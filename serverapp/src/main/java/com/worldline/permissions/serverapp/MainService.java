package com.worldline.permissions.serverapp;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import java.util.Random;
import java.util.UUID;

/**
 * Created by a557114 on 08/10/2015.
 */
public class MainService extends Service {

    private final IBinder mBinder = new LocalBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    private class LocalBinder extends IConnectionInterface.Stub {

        private final Random randomGenerator;

        public LocalBinder() {
            randomGenerator = new Random();
        }

        @Override
        public int basicInt() throws RemoteException {
            return randomGenerator.nextInt();
        }

        @Override
        public long basicLong() throws RemoteException {
            return randomGenerator.nextLong();
        }

        @Override
        public boolean basicBoolean() throws RemoteException {
            return randomGenerator.nextBoolean();
        }

        @Override
        public float basicFloat() throws RemoteException {
            return randomGenerator.nextFloat();
        }

        @Override
        public double basicDouble() throws RemoteException {
            return randomGenerator.nextDouble();
        }

        @Override
        public String basicString() throws RemoteException {
            return UUID.randomUUID().toString();
        }
    }
}
