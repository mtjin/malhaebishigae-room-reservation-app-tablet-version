package com.mtjin.free_room_tablet.views.code_input

import com.mtjin.free_room_tablet.di.ActivityScope
import dagger.Subcomponent

// Scope annotation that the RegistrationComponent uses
// Classes annotated with @ActivityScope will have a unique instance in this Component
@ActivityScope
@Subcomponent
interface CodeInputComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(): CodeInputComponent
    }

    fun inject(codeInputActivity: CodeInputActivity)
}