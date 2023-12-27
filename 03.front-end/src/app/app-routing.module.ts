import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ForgotPasswordComponent} from "./features/account/forgot-password/forgot-password.component";

const routes: Routes = [
  {path:'forgot-password', component:ForgotPasswordComponent}
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
