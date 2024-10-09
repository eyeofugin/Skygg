package game.entities;

import framework.resources.SpriteUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Animator {

    public Map<String, Animation> animations = new HashMap<>();
    public String currentAnim;
    public String defaultAnim;
    public boolean onLoop;
    public int animationCounter = 0;
    public int width;
    public int height;

    public List<Animation> effects = new ArrayList<>();
    public boolean waitFor;
    public int[] image;

    public void animate() {
        Animation anim = animations.get(currentAnim);
        image = anim.getImage(animationCounter);
        if (animationCounter == anim.length) {
            animationCounter = 0;
            if (waitFor) {
                waitFor = false;
            }
            if (!onLoop) {
                currentAnim = defaultAnim;
                onLoop=true;
            }
        }
        animationCounter++;
//        for (int i = 0; i < effects.size(); i++) {
//            animateEffect(i);
//        }
    }
//    public void animateEffect(int effectIndex) {
//        Animation effect = effects.get(effectIndex);
//        int[] effectImage = effect.getImage(effect.animationCounter);
//        if (effect.animationCounter == effect.length) {
//            effect.animationCounter = 0;
//        }
//        int yFrom = effectIndex * 37;
//        this.image = SpriteUtils.mergeIntoSize(this.image, 192, effectImage, 36, 36, 192-36, yFrom);
//        effect.animationCounter++;
//    }
    public void playAnimation(String animName, boolean waitFor) {
        if (this.animations.containsKey(animName)) {
            currentAnim = animName;
            animationCounter = 0;
            this.waitFor = waitFor;
            onLoop = false;
        }
    }
    public void addEffectAnimation(Animation animation) {
        this.effects.add(animation);
    }
    public void removeEffectAnimation(String name) {
        this.effects.removeIf(e->e.name.equals(name));
    }
}
