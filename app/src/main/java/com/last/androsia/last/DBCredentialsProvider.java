package com.last.androsia.last;

import android.content.Context;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.regions.Regions;

/**
 * Created by SPhilipps on 1/31/2017.
 */

public class DBCredentialsProvider {
    public static final String STR_BUCKET = "androsialast";

    private static CognitoCachingCredentialsProvider INSTANCE;

    public static CognitoCachingCredentialsProvider get(Context context){
        if(INSTANCE == null){
            INSTANCE = new CognitoCachingCredentialsProvider(
                    context,
                    xxxxx,
                    Regions.US_WEST_2
            );
        }
        return INSTANCE;
    }
}
