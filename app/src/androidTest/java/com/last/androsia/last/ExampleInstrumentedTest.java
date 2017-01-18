package com.last.androsia.last;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.amazonaws.auth.AWSSessionCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.regions.Regions;
import com.amazonaws.regions.Region;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.*;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.*;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                appContext,
                XXXXX,
                Regions.US_WEST_2 // Region
        );
        AWSSessionCredentials arnCredentials = credentialsProvider.getCredentials();

        AmazonDynamoDBClient dynamoDB = new AmazonDynamoDBClient(arnCredentials);
        dynamoDB.setRegion(Region.getRegion(Regions.US_WEST_2));
        DynamoDBMapper mapper = new DynamoDBMapper(dynamoDB);
        TagsListItem putItem = new TagsListItem();
        putItem.setCtrOwned(10);
        putItem.setCtrSeen(20);
        putItem.setId("5");
        putItem.setImageUrl("https://s-media-cache-ak0.pinimg.com/236x/59/87/6c/59876c15abd6d705ea8b87033633f009.jpg");
        putItem.setTitle("put item");
        putItem.setType(2);
        //mapper.save(putItem);

        TagsListItem item = mapper.load(TagsListItem.class, "0");
        //int i = 2;
    }
}
