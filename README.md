https://docs.jboss.org/hibernate/orm/6.5/userguide/html_single/Hibernate_User_Guide.html#appendix-monitoring-with-JFR

Limitations
-----------

- Stack traces are disabled by default and need to be enabled on a per event basis.

```xml
    <event name="org.hibernate.orm.JdbcPreparedStatementExecution">
      <setting name="enabled">true</setting>
      <setting name="stackTrace">true</setting>
      <setting name="threshold">200 ms</setting>
    </event>
```
