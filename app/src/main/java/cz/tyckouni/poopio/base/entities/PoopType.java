package cz.tyckouni.poopio.base.entities;

import java.util.Arrays;
import java.util.List;

import cz.tyckouni.poopio.R;

import static cz.tyckouni.poopio.base.entities.PoopAttributeType.COLOR;
import static cz.tyckouni.poopio.base.entities.PoopAttributeType.DEER_COUNT;
import static cz.tyckouni.poopio.base.entities.PoopAttributeType.SIZE;

public enum PoopType {
    SNAKE(R.drawable.img_poop_snake,
       COLOR,
       SIZE
    ),
    EXPLOSION(R.drawable.img_poop_explosion,
       COLOR,
       SIZE
    ),
    DEER(R.drawable.img_poop_deer,
       COLOR,
       DEER_COUNT
    ),
    FLOATER(R.drawable.img_poop_floater,
       COLOR,
       SIZE
    ),
    CHILDBIRTH(R.drawable.img_poop_childbirth,
       COLOR,
       SIZE
    ),
    SPLASH(R.drawable.img_poop_splash,
       COLOR,
       SIZE
    ),
    SUBMARINE(R.drawable.img_poop_submarine,
       COLOR,
       SIZE
    ),
    TITANIC(R.drawable.img_poop_titanic,
       COLOR,
       SIZE
    );

    private List<PoopAttributeType> requiredAttributes;
    private int resourceId;

    PoopType(int resourceId, PoopAttributeType... requiredAttributes) {
        this.requiredAttributes = Arrays.asList(requiredAttributes);
        this.resourceId = resourceId;
    }

    public List<PoopAttributeType> getRequiredAttributes() {
        return requiredAttributes;
    }

    public int getResourceId() {
        return resourceId;
    }
}
