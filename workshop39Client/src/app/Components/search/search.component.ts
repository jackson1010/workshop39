import { Component, OnDestroy, OnInit, inject } from '@angular/core';
import { MarvelService } from '../marvel.service';
import { ActivatedRoute, Params, Router } from '@angular/router';
import { CharacterList } from '../models';
import { Observable, Subscription } from 'rxjs';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css'],
})
export class SearchComponent implements OnInit, OnDestroy {
  marvelSvc = inject(MarvelService);
  router = inject(Router);
  route = inject(ActivatedRoute);
  result$!: Observable<CharacterList>;
  subscription!: Subscription;
  characterList!: CharacterList;
  name = '';
  characterId!:number;
  selectedIndex!: number;


  ngOnInit(): void {
    this.name = this.route.snapshot.params['name'];
    console.log(this.name);
    this.result$ = this.marvelSvc.getCharacter(this.name);
    this.subscription = this.result$.subscribe((result: CharacterList) => {
      this.characterList = result;
    });
  }

  ngOnDestroy() {
    this.subscription.unsubscribe();
  }

  selected(i: number) {
    console.log(this.characterList.characters[i].id);
    this.characterId=this.characterList.characters[i].id;
    this.selectedIndex = i;
  }

  process() {
    const queryParams: Params={id: this.characterId }
    this.router.navigate(['/result'],{queryParams});
  }
}
