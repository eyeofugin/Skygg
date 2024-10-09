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
        int[] skillNamePixels = getTextLine(this.skill.translation, this.width, 10, 1,
                TextAlignment.LEFT, Color.VOID, Color.WHITE);
        fillWithGraphicsSize(0, 0, this.width, 10, skillNamePixels, false);
        String costString = "Cost:" + skill.getCostString();
        int[] costPixels = getTextLine(costString, this.width, 10, 1,
                TextAlignment.LEFT, Color.VOID, Color.WHITE);
        fillWithGraphicsSize(0, 12, this.width, 10, costPixels, false);
        int[] descriptionPixels = getTextBlock(this.skill.getDescriptionFor(this.skill.entity), this.width, 1, TextAlignment.LEFT, Color.VOID, Color.WHITE);
        int height = descriptionPixels.length / this.width;
        fillWithGraphicsSize(0, 24,  this.width, height, descriptionPixels, false);
        return pixels;
    }
}
