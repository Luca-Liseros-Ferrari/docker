import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {ProductListComponent} from "./product-list/product-list.component";
import {CartComponent} from "./cart/cart.component";
import {HttpClientModule} from "@angular/common/http";
import {CommonModule} from "@angular/common";


@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, CommonModule, HttpClientModule],
  template: `
    <router-outlet></router-outlet>
  `,

  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'beats-app';
}
