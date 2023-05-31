import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { Character, CharacterList } from './models';

const URL = '/api/characters';
const URLBYID = '/api/character';

@Injectable()
export class MarvelService {
  http = inject(HttpClient);

  getCharacter(name: string): Observable<CharacterList> {
    const params = new HttpParams().set('name', name);
    return this.http.get<CharacterList>(URL, { params });
  }

  getCharacterByID(characterId: number): Observable<Character> {
    console.log(URLBYID + '/' + characterId);
    return this.http.get<Character>(URLBYID + '/' + characterId);
  }

  insertComment(characterId: number, comment: string):Observable<string> {
    // {
    // "comment": this.comment
    // }
    const jsonComment = JSON.stringify({ comment: comment });
    console.log(jsonComment);
    console.log(URLBYID + '/' + characterId);   

    const headers = new HttpHeaders()
      .set('Content-Type', 'application/json')
      .set('Accept', 'application/json');
     return this.http.post<any>(URLBYID + '/' + characterId, jsonComment, { headers });
  }
}
