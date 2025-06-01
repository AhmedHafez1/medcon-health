import { Component } from '@angular/core';
import { MatButton } from '@angular/material/button';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-doctors',
  imports: [MatButton, RouterLink],
  templateUrl: './doctors.component.html',
  styleUrl: './doctors.component.scss',
})
export class DoctorsComponent {}
