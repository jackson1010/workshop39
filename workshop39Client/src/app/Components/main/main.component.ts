import { Component, OnInit, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.css'],
})
export class MainComponent implements OnInit {
  marvelForm!: FormGroup;
  fb = inject(FormBuilder);
  router=inject(Router);

  ngOnInit(): void {
    this.marvelForm = this.createForm();
  }
  createForm() {
    return this.fb.group({
      name: this.fb.control<string>('', [
        Validators.required,
        Validators.minLength(5),
      ]),
    });
  }

  processForm(){
    const name=this.marvelForm.value["name"];
    this.router.navigate(['/search', name ]);
  }
}
