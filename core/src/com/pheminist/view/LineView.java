package com.pheminist.view;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.pheminist.model.Model;

import static com.pheminist.model.Model.SKIN;

public class LineView extends Table {
    public LineView(Model model) {
        final Skin skin = model.assetManager.get(SKIN, Skin.class);

        setBackground(skin.getDrawable("pix_white"));
    }
}
