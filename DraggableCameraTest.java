import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class DraggableCameraTest extends AbstractScreen {
    DraggableCameraProcessor draggableCameraProcessor;
    Image image;

    Stage hud_stage;
    public DraggableCameraTest(Test g) {
        super(g);
        image = new Image(new Texture(Gdx.files.internal("map.gif")));
        image.setPosition(0, 0);

        draggableCameraProcessor = new DraggableCameraProcessor(stage.getCamera(), image.getWidth(), image.getHeight());
        multiplexer.addProcessor(0, draggableCameraProcessor);          // Important!! add it to multiplexer and put it on the top as possible

        hud_stage = new Stage();
        multiplexer.addProcessor(hud_stage);

        TextButton back = new TextButton("back", skin);
        back.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        final TextButton bound = new TextButton("", skin);
        if(draggableCameraProcessor.isBounded()) bound.setText("unbound");
        else bound.setText("bound");

        bound.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (draggableCameraProcessor.isBounded()) {
                    draggableCameraProcessor.unbound();
                    bound.setText("bound");
                } else {
                    draggableCameraProcessor.setBound(image.getWidth(), image.getHeight());
                    bound.setText("unbound");
                }
            }
        });

        Table table = new Table(skin);
        table.setFillParent(true);
        table.defaults().padLeft(5).padTop(5);
        table.left().top();
        table.add(back);
        table.add(bound);
        hud_stage.addActor(table);

        stage.addActor(image);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        hud_stage.act();
        hud_stage.draw();

    }
}
