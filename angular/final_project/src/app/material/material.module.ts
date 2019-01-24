import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import * as Material from '@angular/material';

@NgModule({
  imports: [
    CommonModule,
    Material.MatInputModule,
    Material.MatNativeDateModule,
    Material.MatDatepickerModule,
    Material.MatSliderModule,
    Material.MatFormFieldModule,
    Material.MatDialogModule,
    Material.MatRadioModule,
    Material.MatSelectModule,
    Material.MatButtonModule,
    Material.MatCheckboxModule
  ],
  exports: [
    Material.MatInputModule, 
    Material.MatNativeDateModule,
    Material.MatDatepickerModule,
    Material.MatSliderModule,
    Material.MatFormFieldModule,
    Material.MatDialogModule,
    Material.MatRadioModule,
    Material.MatSelectModule,
    Material.MatButtonModule,
    Material.MatCheckboxModule
  ], 
  declarations: []
})
export class MaterialModule { }
