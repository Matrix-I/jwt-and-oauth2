import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { LoginComponent } from './login/login.component';
import { AppRoutingModule } from './app.routes';
import { SharedModule } from './sharing/shared.module';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [LoginComponent],
  imports: [AppRoutingModule, BrowserModule, RouterModule, SharedModule],
  providers: []
})
export class AppModule {}
