package com.github.marschall.hibernate.jfr.demo;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.jupiter.api.BeforeEach;

import com.github.marschall.hibernate.jfr.demo.entity.Post;

class JfrDemoTests {
  
  private SessionFactory sessionFactory;

  @BeforeEach
  void setUp() {
    // A SessionFactory is set up once for an application!
    StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .build();
    try {
      sessionFactory = new MetadataSources(registry)
              .addAnnotatedClass(Post.class)
              .buildMetadata()
              .buildSessionFactory();
    } catch (Exception e) {
      // The registry would be destroyed by the SessionFactory, but we
      // had trouble building the SessionFactory so destroy it manually.
      StandardServiceRegistryBuilder.destroy(registry);
      throw e;
    }
  }
}
