declare namespace Cypress{
    interface Chainable {
        /**
         * Navigate to main page and register as admin
         */
        registerUser();

        /**
         * Navigate to main page and login as user
         */
        loginUser();
    }
}
