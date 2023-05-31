import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MainComponent } from './Components/main/main.component';
import { SearchComponent } from './Components/search/search.component';
import { ResultComponent } from './Components/result/result.component';
import { CommentComponent } from './Components/comment/comment.component';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveFormsModule } from '@angular/forms';
import { MarvelService } from './Components/marvel.service';

@NgModule({
  declarations: [
    AppComponent,
    MainComponent,
    SearchComponent,
    ResultComponent,
    CommentComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    ReactiveFormsModule,
  ],
  providers: [MarvelService],
  bootstrap: [AppComponent]
})
export class AppModule { }
