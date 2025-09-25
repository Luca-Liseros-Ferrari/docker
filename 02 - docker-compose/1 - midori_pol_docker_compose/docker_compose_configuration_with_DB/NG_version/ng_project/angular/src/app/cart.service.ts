import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, BehaviorSubject } from 'rxjs';
import { v4 as uuidv4 } from 'uuid';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private cartVisibilitySubject = new BehaviorSubject<boolean>(false);
  cartVisibility$ = this.cartVisibilitySubject.asObservable();

  private apiUrl = 'http://localhost:8080/midoripol/carrello';
  constructor(private http: HttpClient) {}

  addProductToCart (product: any, quantity: number): Observable<any> {
    const userId = this.getUserId();
    const data = { ...product, quantity, userId };
    this.updateCartVisibility(true); // Aggiunto per aggiornare la visibilità dell'icona del carrello
    return this.http.post(`${this.apiUrl}/add`,  data, { responseType: 'text' });
  }

  removeProductFromCart (productId: number, quantity: number): Observable<any> {
    const userId = this.getUserId();
    const data = { id: productId, quantity, userId };
    return this.http.post(`${this.apiUrl}/less`, data, { responseType: 'text' });
  }

  deleteCart (cart: any): Observable<any> {
    const userId = this.getUserId();
    const data = { cartProducts: cart, userId };
    this.updateCartVisibility(false); // Aggiunto per nascondere l'icona del carrello
    return this.http.post(`${this.apiUrl}/delete`, data, { responseType: 'text' });
  }

  viewCart (): Observable<any> {
    const userId = this.getUserId();
    return this.http.get<any>(`${this.apiUrl}/viewCart?userId=${userId}`);
  }

  getUserCart (): Observable<any> {
    const userId = this.getUserId();
    return this.http.get<any>(`${this.apiUrl}/viewCart?userId=${userId}`);
  }

  private getUserId (): string {
    let userId = localStorage.getItem('userId');
    if (!userId) {
      userId = this.generateUniqueId();
      localStorage.setItem('userId', userId);
    }
    return userId;
  }

  private generateUniqueId (): string {
    return uuidv4();
  }

  calculateTotalQuantity (cartProducts: any[]): number {
    return cartProducts.reduce((total, product) => total + product.quantity, 0);
  }

  updateCartVisibility (isVisible: boolean): void {
    this.cartVisibilitySubject.next(isVisible);
  }
}
