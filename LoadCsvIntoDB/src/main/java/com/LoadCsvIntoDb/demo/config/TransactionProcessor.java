package com.LoadCsvIntoDb.demo.config;
 
import org.springframework.batch.item.ItemProcessor;

import com.LoadCsvIntoDb.demo.CommonBatchUtils;
import com.LoadCsvIntoDb.demo.model.Transaction;


 
public class TransactionProcessor implements ItemProcessor<Transaction, Transaction>
{
    public Transaction process(Transaction trx) throws Exception
    {
    	trx.setAccountNumber("A" + trx.getAccountNumber());
    	String strDate = trx.getStrCreateDate();
    	String strTime = trx.getStrCreateTime();
    	trx.setCreateDate(CommonBatchUtils.getDateTimeFromString(strDate, strTime));
        System.out.println("Process Transaction : " + trx);
        return trx;
    }
}