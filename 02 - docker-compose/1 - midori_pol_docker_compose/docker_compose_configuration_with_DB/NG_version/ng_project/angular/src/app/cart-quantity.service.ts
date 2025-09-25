// cart-quantity.service.ts

import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CartQuantityService {
  private trackQuantitySubject = new BehaviorSubject<number>(0);
  trackQuantity$ = this.trackQuantitySubject.asObservable();

  constructor() {}

  updateTrackQuantity(newQuantity: number): void {
    this.trackQuantitySubject.next(newQuantity);
  }
}
