package com.spring.xmltomysql;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.batch.item.xml.builder.StaxEventItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.WritableResource;
import org.springframework.oxm.xstream.XStreamMarshaller;
import org.springframework.transaction.PlatformTransactionManager;

import com.thoughtworks.xstream.security.NoTypePermission;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;

import jakarta.persistence.EntityManagerFactory;

@Configuration

public class XmlBatchConfig {


	 	private final JobRepository jobRepository;
	    private final PlatformTransactionManager transactionManager;
	    private final EntityManagerFactory entityManagerFactory;

	    public XmlBatchConfig(JobRepository jobRepository, PlatformTransactionManager transactionManager,
	                          EntityManagerFactory entityManagerFactory) {
	        this.jobRepository = jobRepository;
	        this.transactionManager = transactionManager;
	        this.entityManagerFactory = entityManagerFactory;
	    }

	    @Bean
	    public StaxEventItemReader<Person> itemReader() {
	        return new StaxEventItemReaderBuilder<Person>()
	                .resource(new FileSystemResource("/Users/shamim_ahamed/Documents/html/persons (1).xml"))
	                .addFragmentRootElements("person")
	                .unmarshaller(PersonMarshaller())
	                .name("xml")
	                .build();
	    }

	    @Bean
	    public XStreamMarshaller PersonMarshaller() {
	        Map<String, Class> aliases = new HashMap<>();
	        aliases.put("person", Person.class);
	        aliases.put("id", BigDecimal.class);
	        aliases.put("firstName", String.class);
	        aliases.put("lastName", String.class);
	        aliases.put("age", Integer.class);

	        XStreamMarshaller marshaller = new XStreamMarshaller();
	        marshaller.setAliases(aliases);
	        
	        // Configure XStream security
	        marshaller.getXStream().addPermission(NoTypePermission.NONE); // clear out existing permissions
	        marshaller.getXStream().addPermission(NullPermission.NULL);   // allow null
	        marshaller.getXStream().addPermission(PrimitiveTypePermission.PRIMITIVES); // allow primitives
	        marshaller.getXStream().allowTypes(new Class[]{Person.class}); // allow Person class
	        marshaller.getXStream().allowTypesByWildcard(new String[]{ 
	            "java.util.*",
	            "java.lang.*",
	            "com.spring.xmltomysql.*"  // allow classes in your package
	        });
	        
	        return marshaller;
	    }

	    // Rest of the code remains the same...
//	    @Bean
//	    public WritableResource outputResource() {
//	        return new FileSystemResource("/Users/shamim_ahamed/Documents/html/persons (1).xml");
//	    }

//	    @Bean
//	    public StaxEventItemWriter<Person> itemWriter(WritableResource outputResource) {
//	        return new StaxEventItemWriterBuilder<Person>()
//	                .marshaller(PersonMarshaller())
//	                .resource(outputResource)
//	                .rootTagName("person")
//	                .name("personWriter")  
//	                .overwriteOutput(true)
//	                .build();
//	    }

	    @Bean
	    public ItemProcessor<Person, Person> processor() {
	        return person -> {
	            System.out.println("Processing person: " + person);
	            return person;
	        };
	    }

	    @Bean
	    public JpaItemWriter<Person> jpaItemWriter() {
	        JpaItemWriter<Person> writer = new JpaItemWriter<>();
	        writer.setEntityManagerFactory(entityManagerFactory);
	        return writer;
	    }

	    @Bean
	    public Step step1() {
	        return new StepBuilder("step1", jobRepository)
	                .<Person, Person>chunk(10, transactionManager)
	                .reader(itemReader())
	                .processor(processor())
	                .writer(jpaItemWriter())
	                .build();
	    }

	    @Bean
	    public Job importPersonJob(Step step1) {
	        return new JobBuilder("importPersonJob", jobRepository)
	                .start(step1)
	                .build();
	    }

}