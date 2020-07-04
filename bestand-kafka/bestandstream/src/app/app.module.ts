import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import {AppComponent} from './app.component';
import {ButtonModule} from 'primeng/button';
import {InputTextareaModule} from 'primeng/inputtextarea';
import {ChartModule} from 'primeng/chart';
import {BestandStreamService} from "./bestandstream.service";

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    ButtonModule,
    InputTextareaModule,
    FormsModule,
    ChartModule,
    HttpClientModule
  ],
  exports: [
    ButtonModule,
    FormsModule,
    InputTextareaModule,
    ChartModule,
    HttpClientModule
  ],
  providers: [BestandStreamService],
  bootstrap: [AppComponent]
})
export class AppModule { }
