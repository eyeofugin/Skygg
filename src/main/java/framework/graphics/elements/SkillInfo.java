package framework.graphics.elements;

import framework.graphics.GUIElement;
import framework.graphics.text.Color;
import framework.graphics.text.TextAlignment;
import game.skills.Skill;

public class SkillInfo extends GUIElement {
    private final Skill skill;

    public SkillInfo(Skill skill) {
        this.skill = skill;
        this.setSize(208, 88);
    }
    @Override
    public int[] render() {
        int[] skillNamePixels = getTextLine(this.skill.getName(), this.width/2, 10,
                TextAlignment.LEFT, Color.VOID, Color.WHITE);
        fillWithGraphicsSize(0, 0, this.width/2, 8, skillNamePixels, false);

        String costString = skill.getCostString();
        int[] costPixels = getTextLine(costString, this.width/2, 10,
                TextAlignment.RIGHT, Color.VOID, Color.WHITE);
        fillWithGraphicsSize(this.width/2, 0, this.width/2, 8, costPixels, false);

        String dmgOrHealString = skill.getDmgOrHealString();
        int[] dmgOrHealPixels = getTextLine(dmgOrHealString, this.width/2, 10,
                TextAlignment.LEFT, Color.VOID, Color.WHITE);
        fillWithGraphicsSize(0, 8, this.width/2, 8, dmgOrHealPixels, false);

        String targetString = skill.getTargetString();
        int[] targetStringPixels = getTextLine(targetString, this.width/2, 10,
                TextAlignment.RIGHT, Color.VOID, Color.WHITE);
        fillWithGraphicsSize(this.width/2, 9,this.width/2, 8, targetStringPixels, false);


        int[] descriptionPixels = getTextBlock(this.skill.getDescriptionFor(this.skill.hero), this.width, Color.VOID, Color.WHITE);
        int height = descriptionPixels.length / this.width;
        fillWithGraphicsSize(0, 18,  this.width, height, descriptionPixels, false);
        return pixels;
    }
}
