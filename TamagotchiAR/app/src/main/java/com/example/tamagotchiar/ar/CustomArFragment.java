package com.example.tamagotchiar.ar;

import com.example.tamagotchiar.ArActivity;
import com.google.ar.core.Config;
import com.google.ar.core.Session;
import com.google.ar.sceneform.ux.ArFragment;

import java.util.Objects;

public class CustomArFragment extends ArFragment {
    @Override
    protected Config getSessionConfiguration(Session session) {
        Config config = new Config(session);
        config.setUpdateMode(Config.UpdateMode.LATEST_CAMERA_IMAGE);
        config.setFocusMode(Config.FocusMode.AUTO);
        session.configure(config);

        this.getArSceneView().setupSession(session);

        ((ArActivity) Objects.requireNonNull(getActivity())).setupDatabase(config,session);
        return config;
    }
}
