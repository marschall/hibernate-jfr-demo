package com.github.marschall.hibernate.jfr.demo;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
import org.hibernate.event.jfr.internal.FlushEvent;
import org.hibernate.event.jfr.internal.JdbcBatchExecutionEvent;
import org.hibernate.event.jfr.internal.JdbcPreparedStatementCreationEvent;
import org.hibernate.event.jfr.internal.JdbcPreparedStatementExecutionEvent;
import org.hibernate.event.jfr.internal.PartialFlushEvent;
import org.hibernate.query.Query;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.github.marschall.hibernate.jfr.demo.entity.Actor;
import com.github.marschall.hibernate.jfr.demo.entity.Film;

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
    recording.enable(JdbcPreparedStatementCreationEvent.class);
    recording.enable(JdbcPreparedStatementExecutionEvent.class);
    recording.enable(JdbcBatchExecutionEvent.class);
    recording.enable(FlushEvent.class);
    recording.enable(PartialFlushEvent.class);
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
      recordingStream.enable(JdbcPreparedStatementCreationEvent.class).withThreshold(Duration.ofMillis(100L));
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
            .addAnnotatedClass(Actor.class)
            .addAnnotatedClass(Film.class)
            // H2 Sakila
            .setProperty(AvailableSettings.JAKARTA_JDBC_URL, "jdbc:h2:mem:sakila;INIT=RUNSCRIPT FROM '~/src/test/resources/sakila.sql'")
            // Credentials
            .setProperty(AvailableSettings.JAKARTA_JDBC_USER, "sa")
            .setProperty(AvailableSettings.JAKARTA_JDBC_PASSWORD, "sa")
            .setProperty(AvailableSettings.STATEMENT_FETCH_SIZE, 100)
            .setProperty(AvailableSettings.PHYSICAL_NAMING_STRATEGY, CamelCaseToUnderscoresNamingStrategy.class.getName())
            .setProperty(AvailableSettings.JAKARTA_SHARED_CACHE_MODE, SharedCacheMode.NONE.name())
//            .setProperty(AvailableSettings.JAKARTA_TRANSACTION_TYPE, PersistenceUnitTransactionType.RESOURCE_LOCAL.name())
            // Create a new SessionFactory
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
               AND name LIKE :namePattern
            """, Film.class);
        List<Film> films = filmQuery
            .setParameter("releaseYear", Year.of(2007))
            .setParameter("namePattern", "F%")
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
            .getResultList();
        assertNotNull(actors);
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
