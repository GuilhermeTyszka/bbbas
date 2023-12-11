// dynamic-html.service.ts

import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class DynamicHtmlService {
  constructor(private http: HttpClient) {}

  getDynamicHtml(): Observable<string> {
    // Substitua 'sua_url' pela URL real que retorna o HTML desejado
    return this.http.get<string>('sua_url');
  }
}

// dynamic-html.component.ts

import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-dynamic-html',
  template: '<div [innerHTML]="dynamicHtml"></div>',
})
export class DynamicHtmlComponent {
  @Input() dynamicHtml: string = '';
}

// parent.component.ts

import { Component, OnInit } from '@angular/core';
import { DynamicHtmlService } from './dynamic-html.service';

@Component({
  selector: 'app-parent',
  template: `
    <div>
      <h1>Minha Página</h1>
      <app-dynamic-html [dynamicHtml]="dynamicHtml"></app-dynamic-html>
    </div>
  `,
})
export class ParentComponent implements OnInit {
  dynamicHtml: string = '';

  constructor(private dynamicHtmlService: DynamicHtmlService) {}

  ngOnInit() {
    this.dynamicHtmlService.getDynamicHtml().subscribe((html) => {
      this.dynamicHtml = html;
    });
  }
}

// app.module.ts

import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { DynamicHtmlComponent } from './dynamic-html.component';
import { DynamicHtmlService } from './dynamic-html.service';
import { ParentComponent } from './parent.component';

@NgModule({
  declarations: [DynamicHtmlComponent, ParentComponent],
  imports: [BrowserModule, HttpClientModule],
  providers: [DynamicHtmlService],
  bootstrap: [ParentComponent],
})
export class AppModule {}
