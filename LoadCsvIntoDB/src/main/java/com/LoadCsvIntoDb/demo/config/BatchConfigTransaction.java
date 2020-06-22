package com.LoadCsvIntoDb.demo.config;

import javax.sql.DataSource;

import org.jboss.logging.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import com.LoadCsvIntoDb.demo.CommonBatchUtils;
import com.LoadCsvIntoDb.demo.model.Transaction;

@Configuration
@EnableBatchProcessing
public class BatchConfigTransaction {

	private static Logger logger = Logger.getLogger(BatchConfigTransaction.class);

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Value("classPath:/input/dataSource.txt")
    private Resource inputResource;

    /**
     * JobBuilderFactory(JobRepository jobRepository)  Convenient factory for a JobBuilder which sets the JobRepository automatically
     */
    @Bean
    public Job readTransactionFileJob() {
        return jobBuilderFactory
                .get("readTransactionFileJob")
                .incrementer(new RunIdIncrementer())
                .start(step())
                .build();
    }
    
    /**
     * StepBuilder which sets the JobRepository and PlatformTransactionManager automatically
     */

    @Bean
    public Step step() {
        return stepBuilderFactory
                .get("step")
                .<Transaction, Transaction>chunk(20)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }
    
    /**
     * Prints the Logs in the console.
     * @return
     */

    @Bean
    public ItemProcessor<Transaction, Transaction> processor() {
        return new TransactionProcessor();
    }

    /**
     * FlatFileItemReader<T> Restartable ItemReader that reads lines from input setResource(Resource).
     * @return
     */
    
    @Bean
    public FlatFileItemReader<Transaction> reader() {
        FlatFileItemReader<Transaction> itemReader = new FlatFileItemReader<Transaction>();
        itemReader.setLineMapper(lineMapper());
        itemReader.setLinesToSkip(1);
        itemReader.setResource(inputResource);
        return itemReader;
    }
    


    /**
     * The data source object is defined and created depending upon the type of database we use.
     * In this example we are using H2 databse so we 'schema-h2.sql'  for example if we are using mysql database
     * we will be using 'schema-mysql.sql'
     *
     * schema-drop-h2.sql will drop the batch related jobs
     *
     * schema-h2.sql is responsible for creating a schema related to the batch job instances in h2 database.
     * BATCH_STEP_EXECUTION_CONTEXT
     * BATCH_JOB_EXECUTION_CONTEXT
     * BATCH_STEP_EXECUTION
     * BATCH_JOB_EXECUTION_PARAMS
     * BATCH_JOB_EXECUTION
     * BATCH_JOB_INSTANCE
     *
     * hotels.sql will create HOTLE table in h2 database
     *
     */

    @Bean
    public DataSource dataSource() {

        EmbeddedDatabaseBuilder embeddedDatabaseBuilder = new EmbeddedDatabaseBuilder();

        return embeddedDatabaseBuilder.addScript("classpath:org/springframework/batch/core/schema-drop-h2.sql")
                .addScript("classpath:org/springframework/batch/core/schema-h2.sql")
                .addScript("classpath:transactions.sql")
                .setType(EmbeddedDatabaseType.H2)
                .build();
    }
    

    /**
     * The itemWriter object will set JDBC connection and sql statement is prepared for the batch action we want to perform in the database.
     * A convenient implementation for providing BeanPropertySqlParameterSource when the item has JavaBean properties that correspond to names used for parameters in the SQL statement.
     *
     */

    @Bean
    public JdbcBatchItemWriter<Transaction> writer() {
        JdbcBatchItemWriter<Transaction> itemWriter = new JdbcBatchItemWriter<Transaction>();
        		
        itemWriter.setDataSource(dataSource());
        itemWriter.setSql("INSERT INTO TRANSACTIONS ( ID, ACCOUNT_NUMBER,DESCRIPTION,AMOUNT,CREATE_DATE,CUSTOMER_ID) VALUES ( RANDOM_UUID(), :accountNumber, :description, :amount, :createDate , :customerId )");
        itemWriter.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Transaction>());

logger.info("writer=" + new  BeanPropertyItemSqlParameterSourceProvider<Transaction>().toString());
        return itemWriter;
    }

    /**
     * the lineMapper for mapping lines (strings) to domain objects typically used to map lines read from a file to domain objects on a per line basis.
     * lineTokenizer to split string obtained typically from a file into tokens. In our example we are using DelimitedLineTokenizer that is because we are using csv file.
     * fieldSetMapper to map data obtained from a FieldSet into an object.
     *
     */

    @Bean
    public LineMapper<Transaction> lineMapper() {
        DefaultLineMapper<Transaction> lineMapper = new DefaultLineMapper<Transaction>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        BeanWrapperFieldSetMapper<Transaction> fieldSetMapper = new BeanWrapperFieldSetMapper<Transaction>();

        lineTokenizer.setDelimiter("|");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(new String[]{"accountNumber", "amount", "description", "strCreateDate", "strCreateTime", "customerId"});
        lineTokenizer.setIncludedFields(new int[]{0, 1, 2, 3, 4, 5});
        fieldSetMapper.setTargetType(Transaction.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);
        logger.info("lineMapper=" + lineMapper);

        return lineMapper;
    }


}
