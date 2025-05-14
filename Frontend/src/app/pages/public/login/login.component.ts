import { CommonModule } from '@angular/common';
import { Component, inject } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  selector: 'rb-login',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {

  private fb = inject(FormBuilder);

  constructor() {}

  formLogin = this.fb.group({
    username: ['', [Validators.required]],
    password: ['', Validators.required]
  });

  login(){
    console.log(this.formLogin.value);
  }
}
