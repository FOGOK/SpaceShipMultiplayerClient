package com.fogok.spaceships.view.screens.socserv;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.fogok.dataobjects.ServerState;
import com.fogok.spaceships.Main;
import com.fogok.spaceships.net.NetRootController;
import com.fogok.spaceships.net.UICallBacks;
import com.fogok.spaceships.utils.gamedepended.Assets;
import com.fogok.spaceships.view.screens.ScreenSwitcher;
import com.fogok.spaceships.view.utils.NativeGdxHelper;

public class SocServ implements Screen {

    private Stage stage;
    private NetRootController netRootController;

    public SocServ(NativeGdxHelper nativeGdxHelper, final NetRootController netRootController) {   //TODO: разобраться с final, где они должны стоять, в правилах скринов
        stage = nativeGdxHelper.getStage();
        this.netRootController = netRootController;

        Label.LabelStyle labelStyle = new Label.LabelStyle(nativeGdxHelper.getUiBitmapFont(), Color.WHITE);
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = new TextureRegionDrawable(Assets.getRegion(4));
        textButtonStyle.down = new TextureRegionDrawable(Assets.getRegion(5));
        textButtonStyle.font = nativeGdxHelper.getUiBitmapFont();

        final String infoString =
                "[#00FF00]%s[]\n" +
                "[#FF8000]Винрейт %%: %s[]\n" +
                "[#00FFFF]Игроков в сети: %s";
        final Label infolabel = new Label("", labelStyle);
        infolabel.setText(String.format(infoString, netRootController.getNickName(), 0f, 1));
        Label matchmakingInfo = new Label(String.format("Готов"), labelStyle);

        netRootController.getUiCallBacks().setSocServCallBack(new UICallBacks.SocServCallBack() {
            @Override
            public void recieveServerState(ServerState serverState) {
                infolabel.setText(String.format(infoString, netRootController.getNickName(), "50%", serverState.getPlayersOnline()));
            }

            @Override
            public void exceptionConnect(Throwable cause) {

            }
        });

        TextButton searchGame = new TextButton("Найти игру!", textButtonStyle);

        searchGame.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {

            }
        });

        TextButton leftGameButton = new TextButton("Перезайти (для теста)", textButtonStyle);
        leftGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                netRootController.getNetSocServController().disconnect();
                Main.getScreenSwitcher().setCurrentScreen(ScreenSwitcher.Screens.LOGIN);
            }
        });

        Table table = new Table();
        table.setDebug(true);
        HorizontalGroup horizontalGroup = new HorizontalGroup();
        horizontalGroup.addActor(infolabel);
        horizontalGroup.addActor(leftGameButton);
        table.add(horizontalGroup).expand(true, false);
        table.row();

        VerticalGroup verticalGroup = new VerticalGroup();
        verticalGroup.addActor(searchGame);
        verticalGroup.addActor(matchmakingInfo);
        table.add(verticalGroup).expand();


        table.setFillParent(true);

        stage.addActor(table);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.02f, 0.02f, 0.08f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
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

    }

    @Override
    public void dispose() {
        netRootController.getUiCallBacks().setSocServCallBack(null);
        stage.clear();
    }
}
