package com.pheminist.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Widget;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.ScissorStack;
import com.badlogic.gdx.utils.Align;
import com.pheminist.model.MIDI.HFNote;
import com.pheminist.model.MIDI.HFNoteHandler;
import com.pheminist.model.Model;
import com.pheminist.model.NRModel;

import java.util.List;

import static com.pheminist.model.Model.SKIN;

public class NR extends Widget {
    private static final Color[] notesColors =
            {
                    new Color(0.5f, 1f, 0.5f, 1f), //13
                    new Color(1f, 0f, 0f, 1f),//0
                    new Color(0.5f, 0.5f, 1f, 1f),//14
                    new Color(0f, 0f, 1f, 1f),//2
                    new Color(1f, 0.5f, 0.5f, 1f),//12
                    new Color(0f, 1f, 0f, 1f),//1
                    new Color(1f, 0.5f, 0f, 1f),//3
                    new Color(0f, 1f, 1f, 1f),//10
                    new Color(0.5f, 0f, 1f, 1f),//5
                    new Color(0f, 1f, 0.5f, 1f),//4
                    new Color(0f, 0.5f, 1f, 1f),//8
                    new Color(1f, 0f, 0.5f, 1f),//6
                    new Color(0.5f, 1f, 0f, 1f),//7
                    new Color(1f, 0f, 1f, 1f),//11
                    new Color(1f, 1f, 0f, 1f)//9
            };
    private static final int NUM_OF_COLORS = notesColors.length;
//    private float quarterInScreen = 4f;
    //    float ticksInScreen;
    private final List<HFNote> screenNotes;
    private final HFNoteHandler hData;
    private Model model;
    private NRModel nrModel;
    private TextureRegion img;
    private BitmapFont font;
    private float x, y;
    private float winWidth = 100, winHeight = 100;
    private Rectangle scissors;
    private Rectangle clipBounds;

    public NR(final NRModel nrModel) {
        this.nrModel = nrModel;
        this.screenNotes = nrModel.screenNotes;
        this.model = nrModel.model;

        nrModel.init();
        this.hData = nrModel.gethData();

//        ticksInScreen = quarterInScreen * hData.getPpqn();//*model.getPreferences().getTempVolume();

//        isOns = new boolean[hData.getnTones()];
        Skin skin = model.assetManager.get(SKIN, Skin.class);
        img = skin.getRegion("grad");
        font = skin.getFont("impact");

        scissors = new Rectangle();
        clipBounds = new Rectangle(x, y, winWidth, winHeight);

        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                nrModel.setPaused(!nrModel.isPaused());
            }
        });

        model.noteEvent.getPublisher().addListener(model.beeper);
    }

    public static Color getNoteColor(int note) {
        return notesColors[note % NUM_OF_COLORS];
    }

    public void update(float delta) {
        nrModel.update(delta);
    }

    @Override
    protected void sizeChanged() {
        super.sizeChanged();
        System.out.println("size of NoteRenderer was changed");
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        float curTick = nrModel.time.getTime();
        float sps = model.sps.getSps();

        x = getX();
        y = getY();
        winWidth = getWidth();
        winHeight = getHeight();

        clipBounds.set(x, y, winWidth, winHeight);

        float upt = winHeight / sps;
        float w = winWidth / ((float) hData.getnSoundes());

        ScissorStack.calculateScissors(getStage().getCamera(), batch.getTransformMatrix(), clipBounds, scissors);
        if (ScissorStack.pushScissors(scissors)) {
            for (HFNote hn : screenNotes) {
                float x = hn.getNote() * w;
                float y = winHeight - (curTick + sps - hn.getTime()) * upt;
                float h = hn.getDuration() * upt;
                int n = hn.getNote();

                batch.setColor(getNoteColor(n));
                batch.draw(img, this.x + x, this.y + y, w, h);
            }
            batch.flush();
            ScissorStack.popScissors();

        }

        if (nrModel.isPaused()) {
            font.setColor(Color.YELLOW);
            font.draw(batch, "press to start", 0, winHeight/2,winWidth, Align.center,false);
        }

    }
}
