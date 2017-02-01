package com.last.androsia.last;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;

import java.io.Serializable;

/**
 * Created by SPhilipps on 2/1/2017.
 */

public class DBLastDynamoMapper extends DynamoDBMapper implements Serializable {
    public DBLastDynamoMapper(AmazonDynamoDB dynamoDB) {
        super(dynamoDB);
    }
}
