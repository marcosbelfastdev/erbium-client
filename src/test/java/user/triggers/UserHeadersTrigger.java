package user.triggers;

import base.components.core.base.scripts.HeadersTrigger;

public class UserHeadersTrigger extends HeadersTrigger {

    @Override
    public void run() {

        headersManagerOperator.getHeaders()
                .removeIfHeaderKeyIsEmpty()
                .removeIfValueIsNull()
                .removeDuplicates()
                .removeIfValueIsBlankString();
    }

    @Override
    public void getBasicRequirementHeaders() {

    }

    @Override
    public void getCleanedupHeaders() {

    }
}
