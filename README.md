# Stormpath Notes

## Initialization and Instant-Run (Incremental builds)
* java.lang.IllegalStateException: You may only initialize Stormpath once! will trigger for incremental builds.  Perhaps silently catch and warn instead of error and crash? or initialize Stormpath in Application subclass

## Callbacks
* ~~error.developerMessage() is not clear and usually null, why not just forward the HTTP message to the developerMessage?~~ You do, just in error.message()

## Documentation
* "Use the social provider's iOS SDK to get an access token, and pass it to Stormpath to log in." Should be Android not iOS
* Docs on Social Provider setup shouldn't just give devs facebook docs.  Specifically, since Stormpath only needs certain pieces of information, it should say what it needs so a dev
knows where to stop
    * e.g, FB auth only requires creating the app to get its ID and secret, and setting the callback url (then Stormpath directory setup)


## Issues
* CCT dependency is transitive.  When using support-libs 25.1.0 the application crashes while trying to open CCT
    ** CCT fails on 25+ libs
    ** In order to support devs who are targetting API 25+, the sdk needs to be updated to use the newer 25+ support libs
    ** Solution here? http://stackoverflow.com/questions/41564529/chrome-customtab-error-java-lang-nosuchmethoderror-no-static-method-startactiv

## Proguard
** dontwarn for okio


