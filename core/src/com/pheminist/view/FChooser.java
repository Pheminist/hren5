package com.pheminist.view;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.pheminist.Filer.FileItem;
import com.pheminist.Filer.Filer;
import com.pheminist.controller.Controller;
import com.pheminist.model.MIDI.HFNoteHandler;
import com.pheminist.model.MIDI.HNoteProvider;
import com.pheminist.model.MIDI.MidiData;
import com.pheminist.model.Model;

import java.util.List;

import static com.pheminist.model.Model.SKIN;

public class FChooser extends BaseScreen {
    private final Table table = new Table();
    private final Table scrollTable = new Table();
    private final Table driveTable = new Table();
    private Skin skin;
    private List<FileItem> files;
    private Label pathLabel;
    private Filer filer;


    public FChooser(Controller controller, Model model) {
        super(controller, model);
        skin = this.model.assetManager.get(SKIN, Skin.class);
        filer = new Filer();
    }

    @Override
    public void init() {
        table.clear();
        scrollTable.clear();
        driveTable.clear();

        files = filer.getFileItems(filer.getRoot());

        pathLabel = new Label(filer.getRoot().path(), skin);
        fillTable(files);

        scrollTable.addListener(new ActorGestureListener() {
            @Override
            public void tap(InputEvent event, float x, float y, int count, int button) {
                super.tap(event, x, y, count, button);

                if (Gdx.app.getType() == Application.ApplicationType.Desktop && count < 2) return;

                int row = scrollTable.getRow(y);
                System.out.println("------row------ " + row + "   y=  " + y);
                if (row > files.size() - 1)
                    return;   //  костыль ( бывает y <0 и соотв-но несуществующая row)

                FileHandle file = files.get(row).getFile();

                if (filer.isDir(file)) {
                    System.out.println("---- is dir ------");
                    files = filer.getFileItems(file);
                    pathLabel.setText(file.file().getAbsolutePath().replace('\\', '/'));
                    fillTable(files);
                } else if (filer.isHren(file.file())) {
                    startGame(file);
                }
            }
        });

        final ScrollPane scroller = new ScrollPane(scrollTable);

//        scrollTable.debugAll();
//        table.debugAll();
        table.top();

        table.setFillParent(true);

        TextButton b1 = new TextButton("root", skin);
        b1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                FileHandle file = filer.getRoot();
                files = filer.getFileItems(file);
                pathLabel.setText(file.path());
                fillTable(files);
            }
        });
        Button b2 = new TextButton("embedded",skin);
        Button b3 = new Button(skin);

        pathLabel.setWrap(true);
        driveTable.defaults().expandX().fill().row();
        driveTable.add(b1).row();
        driveTable.add(b2).row();
        driveTable.add(b3).row();

        table.add(pathLabel).colspan(2).expandX().fillX().center().row();
        table.add(driveTable).width(300).top();
//        table.add(pathLabel).fillX();
//        table.row();
        table.add(scroller).expandX().fillX();

        stage.addActor(table);
    }

    private void fillTable(List<FileItem> files) {
        scrollTable.clearChildren();

        Image image;
        Label label;

        for (FileItem fileItem : files) {
            label = new Label(fileItem.getName(), skin);//,"font-title-export", Color.WHITE);//,"font-list",Color.CYAN);
            label.setWrap(true);
            if (fileItem.getType() == FileItem.DIR_TYPE)
                image = new Image(skin.getRegion("folder"));
            else image = new Image(skin.getRegion("midi"));

            scrollTable.add(image).size(label.getHeight());
            scrollTable.add(label).left().expandX().fillX();
            scrollTable.row();
        }
    }

    private void startGame(FileHandle file) {
        model.sethData(new HFNoteHandler(new HNoteProvider(new MidiData(file))));
        controller.changeScreen(GScreen.class);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
    }

}

