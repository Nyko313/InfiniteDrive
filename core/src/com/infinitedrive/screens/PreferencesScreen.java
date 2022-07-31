package com.infinitedrive.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.infinitedrive.InfiniteDrive;
import com.infinitedrive.helpers.Const;
import com.infinitedrive.objects.MenuDecorationTiles;

import javax.swing.text.TabableView;

public class PreferencesScreen  implements Screen {
    final InfiniteDrive game;

    private MenuDecorationTiles menuDecorationTiles;
    private SpriteBatch batch;

    private Stage stage;

    private Label titleLabel;
    private Label volumeMusicLabel;
    private Label volumeSoundLabel;
    private Label musicOnOffLabel;
    private Label soundOnOffLabel;

    public PreferencesScreen(InfiniteDrive game){
        this.game = game;
        stage = new Stage(new ScreenViewport());
        batch = InfiniteDrive.INSTANCE.getBatch();
        menuDecorationTiles = new MenuDecorationTiles();
    }

    @Override
    public void show() {

        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        table.setDebug(false);
        stage.addActor(table);

        Skin skin = new Skin(Gdx.files.internal("sprites/gui/skin/skin.json"));

        final Slider volumeMusicSlider = new Slider(0f, 1f, 0.1f, false, skin);
        volumeMusicSlider.setValue(game.getAppPreferences().getMusicVolume());
        volumeMusicSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                game.getAppPreferences().setMusicVolume(volumeMusicSlider.getValue());
                return false;
            }
        });

        final Slider volumeSoundSlider = new Slider(0f, 1f, 0.1f, false, skin);
        volumeSoundSlider.setValue(game.getAppPreferences().getSoundVolume());
        volumeSoundSlider.addListener(new EventListener() {
            @Override
            public boolean handle(Event event) {
                game.getAppPreferences().setSoundVolume(volumeSoundSlider.getValue());
                return false;
            }
        });

        final TextButton backButton = new TextButton("Back", skin);
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.LoadMenu();
            }
        });

        titleLabel = new Label("Preferences", skin);
        volumeMusicLabel = new Label("Music Volume", skin);
        volumeSoundLabel = new Label("Sound Volume", skin);
        musicOnOffLabel = new Label("Music", skin);
        soundOnOffLabel = new Label("Sound Effect", skin);

        table.add(titleLabel).colspan(2);
        table.row().pad(10, 0, 0, 10);
        table.add(volumeMusicLabel).left();
        table.add(volumeMusicSlider);
        table.row().pad(10, 0, -10, 10);
        table.add(volumeSoundLabel).left();
        table.add(volumeSoundLabel);
        table.row().pad(0, 0, 0, 10);
        table.add(volumeSoundLabel).left();
        table.add(volumeSoundSlider);
        table.row().pad(10, 0, 0, 10);
        table.add(backButton).colspan(2);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        menuDecorationTiles.render();
        batch.end();

        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();

        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
            game.LoadMenu();
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        stage.clear();
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}