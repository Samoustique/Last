package com.last.androsia.last;

import android.content.Context;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;

/**
 * Created by SPhilipps on 1/31/2017.
 */

public class DBCredentialsProvider {

    private static CognitoCachingCredentialsProvider INSTANCE;

    public static CognitoCachingCredentialsProvider get(Context context){
        if(INSTANCE == null){
            INSTANCE = new CognitoCachingCredentialsProvider(
                    context,
                    XXXX,
                    Regions.US_WEST_2
            );
        }
        return INSTANCE;
    }
}
