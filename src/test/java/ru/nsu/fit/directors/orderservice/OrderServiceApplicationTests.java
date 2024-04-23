package ru.nsu.fit.directors.orderservice;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import io.zonky.test.db.flyway.OptimizedFlywayTestExecutionListener;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@SpringBootTest
@FlywayTest
@TestExecutionListeners({
    OptimizedFlywayTestExecutionListener.class,
    DependencyInjectionTestExecutionListener.class,
    DbUnitTestExecutionListener.class
})
@AutoConfigureEmbeddedDatabase
@AutoConfigureMockMvc
class OrderServiceApplicationTests {

	@Test
	void contextLoads() {
	}

}
