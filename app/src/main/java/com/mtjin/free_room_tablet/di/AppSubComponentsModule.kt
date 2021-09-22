package com.mtjin.free_room_tablet.di

import com.mtjin.free_room_tablet.views.code_input.CodeInputComponent
import com.mtjin.free_room_tablet.views.main.MainComponent
import dagger.Module

@Module(subcomponents = [MainComponent::class, CodeInputComponent::class])
class AppSubComponentsModule