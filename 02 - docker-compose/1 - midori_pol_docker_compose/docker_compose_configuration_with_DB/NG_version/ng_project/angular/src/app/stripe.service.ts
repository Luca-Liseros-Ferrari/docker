// STRIPE SERVICE
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { v4 as uuidv4 } from 'uuid';


@Injectable({
  providedIn: 'root'
})

export class StripeService {
  private apiUrl = 'http://localhost:8080/midoripol/stripe';
  constructor (private http: HttpClient) {}

  redirectToStripe(cartProducts: any[]): Observable<any> {
    const userId = this.getUserId();
    const data = { cartProducts, userId };
    return this.http.post(`${this.apiUrl}/confirm`, data, { responseType: 'text' });
  }

  getUserCart(): Observable<any> {
    const userId = this.getUserId();
    return this.http.get<any>(`${this.apiUrl}/viewCart?userId=${userId}`);
  }
  private getUserId(): string {
    let userId = localStorage.getItem('userId');
    if (!userId) {
      userId = this.generateUniqueId();
      localStorage.setItem('userId', userId);
    }
    return userId;
  }

  private generateUniqueId(): string {
    return uuidv4();
  }
}
