import { Component, OnInit, inject } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { MarvelService } from '../marvel.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Observable, Subscription } from 'rxjs';

@Component({
  selector: 'app-comment',
  templateUrl: './comment.component.html',
  styleUrls: ['./comment.component.css'],
})
export class CommentComponent implements OnInit {
  marvelSvc = inject(MarvelService);
  router = inject(Router);
  route = inject(ActivatedRoute);
  result$!:Observable<string>;
  subscription!: Subscription;
  characterId!: number;
  nameResult!: string;
  commentForm!: FormGroup;
  fb = inject(FormBuilder);

  ngOnInit(): void {
    this.nameResult = this.route.snapshot.params['name'];
    this.characterId = this.route.snapshot.queryParams['id'];
    this.commentForm = this.createForm();
  }
  createForm() {
    return this.fb.group({
      comment: this.fb.control<string>('', [
        Validators.required,
        Validators.minLength(10),
      ]),
    });
  }

  processForm() {
    const comment = this.commentForm.value['comment'];
    console.log(comment);
    this.result$= this.marvelSvc.insertComment(this.characterId, comment);
    this.subscription= this.result$.subscribe(
      (result:string) =>{
        console.log(result);
        alert(result);
      }
    )
  }
}
