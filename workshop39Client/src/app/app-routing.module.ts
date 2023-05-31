import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MainComponent } from './Components/main/main.component';
import { SearchComponent } from './Components/search/search.component';
import { ResultComponent } from './Components/result/result.component';
import { CommentComponent } from './Components/comment/comment.component';

const routes: Routes = [
  {path:"", component: MainComponent, title: "ACE"},
  {path:"search/:name", component: SearchComponent, },
  {path:"result", component: ResultComponent, },
  {path:"comment/:name", component: CommentComponent, },
  {path:"**", redirectTo:"/", pathMatch:"full"}
  
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
