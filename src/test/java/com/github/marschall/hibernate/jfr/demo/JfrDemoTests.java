package com.github.marschall.hibernate.jfr.demo;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.time.Duration;
import java.time.Year;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.event.jfr.internal.CacheGetEvent;
import org.hibernate.event.jfr.internal.CachePutEvent;
import org.hibernate.event.jfr.internal.DirtyCalculationEvent;
import org.hibernate.event.jfr.internal.FlushEvent;
import org.hibernate.event.jfr.internal.JdbcBatchExecutionEvent;
import org.hibernate.event.jfr.internal.JdbcConnectionAcquisitionEvent;
import org.hibernate.event.jfr.internal.JdbcConnectionReleaseEvent;
import org.hibernate.event.jfr.internal.JdbcPreparedStatementCreationEvent;
import org.hibernate.event.jfr.internal.JdbcPreparedStatementExecutionEvent;
import org.hibernate.event.jfr.internal.PartialFlushEvent;
import org.hibernate.event.jfr.internal.SessionClosedEvent;
import org.hibernate.event.jfr.internal.SessionOpenEvent;
import org.hibernate.procedure.ProcedureCall;
import org.hibernate.query.Query;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.marschall.hibernate.jfr.demo.entity.Actor;
import com.github.marschall.hibernate.jfr.demo.entity.Address;
import com.github.marschall.hibernate.jfr.demo.entity.Category;
import com.github.marschall.hibernate.jfr.demo.entity.City;
import com.github.marschall.hibernate.jfr.demo.entity.Country;
import com.github.marschall.hibernate.jfr.demo.entity.Film;
import com.github.marschall.hibernate.jfr.demo.entity.FilmText;
import com.github.marschall.hibernate.jfr.demo.entity.Language;
import com.github.marschall.hibernate.jfr.demo.jpa.SpecialFeaturesConverter;
import com.github.marschall.hibernate.jfr.demo.jpa.YearConverter;

import jakarta.persistence.ParameterMode;
import jakarta.persistence.SharedCacheMode;
import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordingStream;

class JfrDemoTests {

  private SessionFactory sessionFactory;

  static final Path RECORDING_LOCATION = Path.of("target", MethodHandles.lookup().lookupClass().getSimpleName() + ".jfr");


  private Recording recording;
  
  @BeforeEach
  void setUp() throws IOException {
    this.initializeSessionFactory();
    this.startRecording();
  }
  
  @AfterEach
  void tearDown() {
    this.stopRecording();
    this.closeSessionFactory();
  }

  void startRecording() throws IOException {
    recording = new Recording();
    recording.enable(JdbcPreparedStatementCreationEvent.class).withStackTrace();
    recording.enable(JdbcPreparedStatementExecutionEvent.class).withStackTrace();
    recording.enable(JdbcBatchExecutionEvent.class);
    recording.enable(FlushEvent.class);
    recording.enable(PartialFlushEvent.class);
    recording.disable(CacheGetEvent.class);
    recording.disable(CachePutEvent.class);
    recording.disable(JdbcConnectionAcquisitionEvent.class);
    recording.disable(JdbcConnectionReleaseEvent.class);
    recording.disable(DirtyCalculationEvent.class);
    recording.disable(SessionClosedEvent.class);
    recording.disable(SessionOpenEvent.class);
    recording.setMaxSize(1L * 1024L * 1024L);
    recording.setToDisk(true);
    recording.setDestination(RECORDING_LOCATION);
    recording.start();
  }
  
  void stopRecording() {
    recording.stop();
  }

  void addListener() {
    try (RecordingStream recordingStream = new RecordingStream()) {
      recordingStream.enable(JdbcPreparedStatementCreationEvent.class);
      recordingStream.enable(JdbcPreparedStatementExecutionEvent.class);
      recordingStream.enable(JdbcBatchExecutionEvent.class);
      recordingStream.enable(FlushEvent.class);
      recordingStream.enable(PartialFlushEvent.class);
      recordingStream.onEvent(JdbcPreparedStatementExecutionEvent.NAME, event -> {
        SqlExecution sqlExecution = SqlExecution.fromEvent(event);
      });
      recordingStream.onEvent(JdbcPreparedStatementCreationEvent.NAME, event -> {
        SqlExecution sqlExecution = SqlExecution.fromEvent(event);
      });
      recordingStream.onEvent(JdbcBatchExecutionEvent.NAME, event -> {
        SqlExecution sqlExecution = SqlExecution.fromEvent(event);
      });
      recordingStream.start();
    }
  }

  record SqlExecution(String sql, long executionTime) {

    static SqlExecution fromEvent(RecordedEvent event) {
      String sql = event.getString("sql");
      long executionTime = event.getLong("executionTime");
      return new SqlExecution(sql, executionTime);
    }

  }

  void initializeSessionFactory() {
    sessionFactory =
        new Configuration()
            // entities
            .addAnnotatedClass(Actor.class)
            .addAnnotatedClass(Address.class)
            .addAnnotatedClass(Category.class)
            .addAnnotatedClass(City.class)
            .addAnnotatedClass(Country.class)
            .addAnnotatedClass(Film.class)
            .addAnnotatedClass(FilmText.class)
            .addAnnotatedClass(Language.class)
            // converters
            .addAnnotatedClass(YearConverter.class)
            .addAnnotatedClass(SpecialFeaturesConverter.class)
            // H2 Sakila
            .setProperty(AvailableSettings.JAKARTA_JDBC_URL, "jdbc:h2:mem:sakila;INIT=RUNSCRIPT FROM 'src/test/resources/sakila.sql'")
            // Credentials
            .setProperty(AvailableSettings.JAKARTA_JDBC_USER, "sa")
            .setProperty(AvailableSettings.JAKARTA_JDBC_PASSWORD, "sa")
            .setProperty(AvailableSettings.STATEMENT_FETCH_SIZE, 100)
            .setProperty("hibernate.type.java_time_use_direct_jdbc", true)
//            .setProperty("hibernate.session.events.log.LOG_QUERIES_SLOWER_THAN_MS", 2)
            .setProperty(AvailableSettings.LOG_SLOW_QUERY, 2)
            .setProperty(AvailableSettings.USE_SQL_COMMENTS, true)
            .setSharedCacheMode(SharedCacheMode.NONE)
            .setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy())
            .buildSessionFactory();
  }
  
  void closeSessionFactory() {
    this.sessionFactory.close();
  }
  
  @Test
  void seraching() {
    this.sessionFactory.inSession(session -> {
      Transaction transaction = session.beginTransaction();
      boolean commit = false;
      try {
        Query<Film> filmQuery = session.createQuery("""
              FROM Film
             WHERE releaseYear < :releaseYear
               AND title LIKE :titlePattern
            """, Film.class);
        List<Film> films = filmQuery
            .setParameter("releaseYear", Year.of(2007))
            .setParameter("titlePattern", "F%")
            .setComment("FilmRepository#findByYearAndTitle")
            .getResultList();
        assertNotNull(films);
        Query<Actor> actorQuery = session.createQuery("""
              FROM Actor
             WHERE firstName = :firstName
                OR lastName = :lastName
            """, Actor.class);
        List<Actor> actors = actorQuery
            .setParameter("firstName", "PENELOPE")
            .setParameter("lastName", "WAHLBERG")
            .setComment("ActorRepository#findByName")
            .getResultList();
        assertNotNull(actors);
        ProcedureCall call = session.createStoredProcedureCall("DB_OBJECT_SQL", String.class)
          .registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
          .registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
          .registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
          .setParameter(1, "TABLE")
          .setParameter(2, "PUBLIC")
          .setParameter(3, "ACTOR");
        boolean hasResult = call.execute();
        assertTrue(hasResult);
        Object createSa = call.getSingleResult();
        assertNotNull(createSa);

        commit = true;
      } finally {
        if (commit) {
          transaction.commit();
        } else {
          transaction.rollback();
        }
      }
    });
  }
  
}
