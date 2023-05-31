import { Component, ElementRef, OnInit, ViewChild, inject } from '@angular/core';
import { Router, ActivatedRoute, Params } from '@angular/router';
import { Observable, Subscription } from 'rxjs';
import { MarvelService } from '../marvel.service';
import { Character } from '../models';

@Component({
  selector: 'app-result',
  templateUrl: './result.component.html',
  styleUrls: ['./result.component.css']
})
export class ResultComponent implements OnInit {
  marvelSvc = inject(MarvelService);
  router = inject(Router);
  route = inject(ActivatedRoute);
  result$!: Observable<Character>;
  characterId!:number;

  ngOnInit(): void {
    this.characterId=this.route.snapshot.queryParams['id'];
    this.result$=this.marvelSvc.getCharacterByID(this.characterId);
  }

  newComment(name:string){ 
    this.router.navigate(['/comment', name],{queryParamsHandling:'preserve'});
  }

}
