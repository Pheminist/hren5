package com.pheminist.view;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.pheminist.controller.Controller;
import com.pheminist.model.Model;

import static com.pheminist.model.Model.SKIN;

public class HelpScreen extends BaseScreen {
    private static final String text = " Данная версия программы находится в состоянии \"в принципе можно пользоваться\".\n" +
            "- открываем заранее подготовленный миди файл, по умолчанию загружен \"Старый клён\".\n"+
            "- внизу на панельке с названием нот или с помощью кнопки Dead notes гасим ноты, которые будем исполнять. "+
            "Эти ноты воспроизводиться не будут.\n"+
            "- в Play маркете вбиваем в поиск \"тюнер\" и скачиваем понравившийся "+
            "(мне понравился \"Бесплатный универсальный тюнер\").\n"+
            "- с помощью скачанного тюнера настраиваем бутылку путем добавления или удаления жидкости.\n"+
            "- если нужно репетируем. Нажатие на основной экран - пауза/продолжение, справа внизу слайдер - тыкаем куда надо, "+
            "R - перемотка в начало\n"+
            "- надеваем наушники, делаем громкость потише, чтобы звук из наушников не был слышен на полученном видео.\n"+
            "- нажимаем Start record и исполняем свою партию.\n"+
            "- в результате получаем файл типа имяисходногомидифайла_2_5.mp4 ,"+
            " где числа - это порядковые номера нот."+
            " Когда в записанных файлах присутствуют все числа от одного до количества нот в произведении, переходим к самому торжественному шагу.\n"+
            "- берем любимый видео редактор (я использовал VSDC free video editor), как понятно из названия он бесплатный,"+
            " и перетаскиваем каждый видеофайл на отдельный слой, компонуем видео на экране чтобы все красиво смотрелось, "+
            "экспортируем проект и наслаждаемся своими музыкальными способностями.\n"+
            " P.S. Параметры shift и tempo у всех файлов, ясное дело, должны быть одинаковыми. Лучше их вообще не трогать.\n"+
            " Пустая литрушечка - 2A - 2A#\n пол-литра - 3C# - 3D#\n 0,45 (современное пиво) - 3E\n"+
            "Искать миди файлы для хренаоке - дело неблагодарное и даже бесполезное. Если у вас есть синтезатор, то это самый простой и правильный путь.\n"+
            "Любой инструмент программа будет воспроизводить звуком бутылок.\n"+
            "Программа понимает миди файлы формата 0 и 1. "+
            "Одинаковые ноты на разных каналах - это разные ноты (сделано на будущее),"+
            "т.е. все ноты должны быть на одном канале, пусть даже на разных треках.\n"+
            "P.P.S. Я не настоящий программист."         ;

    public HelpScreen(final Controller controller, Model model) {
        super(controller, model);
        Label label = new Label(text,model.assetManager.get(SKIN, Skin.class));
        label.setWrap(true);
        Table scrollTable = new Table();
        scrollTable.add(label).pad(10).fill().expand();
        final ScrollPane scroller = new ScrollPane(scrollTable);

        scroller.setFillParent(true);
        stage.addActor(scroller);
        stage.addListener(new InputListener(){
            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if(keycode == Input.Keys.BACK || keycode == Input.Keys.ESCAPE){
                    controller.changeScreen(GScreen.class);
                    return true;
                }
                return false;
            }
        });
        setWorldWidth(800);
    }

    @Override
    public void init() {

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
}
