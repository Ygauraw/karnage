package com.bleatware.karnage.entities;

import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.bleatware.karnage.Drawable;
import com.bleatware.karnage.Drawer;
import com.bleatware.karnage.MainGame;

import java.util.ArrayList;

/**
 * Ator
 * User: vasuman
 * Date: 12/2/13
 * Time: 6:17 PM
 */
public class Player extends PhysicalBody implements Drawable {
    protected Drawer drawer;
    private int health;

    private boolean damaged = false;

    public float getSize() {
        return def.size;
    }


    public void damage() {
        damaged = true;
        if (--health < 0) {
            kill();
        }
    }

    public static class PlayerDef {
        public Vector2 position;
        public int health;
        public float size;
        public float damp;
        public String modelPath;
        public ArrayList<Extension.ExtensionBuilder> builders = new ArrayList<Extension.ExtensionBuilder>();
    }

    @Override
    public Drawer getDrawer() {
        return drawer;
    }

    protected PlayerDef def;

    public Player(final PlayerDef def) {
        super(def.position.x, def.position.y, makeCircle(def.size), false);
        this.def = def;
        this.health = def.health;
        drawer = new Drawer() {
            private Model model = MainGame.assets.get(def.modelPath, Model.class);

            @Override
            public void draw() {
                Vector3 position = new Vector3(getPosition(), 0);
                drawModelAt(model, position);
            }
        };
        super.setDamping(def.damp);
        identifier = EntityType.PLAYER;
        for (Extension.ExtensionBuilder builder : def.builders) {
            Extension extension = builder.build();
            extension.claim(this);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }


    @Override
    public void update(float delT) {
    }

    public boolean isDamaged() {
        if (damaged) {
            damaged = false;
            return true;
        }
        return false;
    }

    public int getHealth() {
        return health;
    }
}
