import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class SidebarService {
  public collapsed: boolean = false;

  constructor() {}

  expand() {
    this.collapsed = false;
  }

  collapse() {
    this.collapsed = true;
  }
}
