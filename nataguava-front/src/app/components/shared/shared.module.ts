import { AlertModule } from './alert/alert.module';
import { AlertComponent } from './alert/alert.component';
import { ButtonModule } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { CardVagaComponent } from './../../components/shared/card-vaga/card-vaga.component';
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

@NgModule({
    declarations: [
       CardVagaComponent
    ],
    imports: [
        CommonModule,
        CardModule,
        ButtonModule
    ],
    exports: [
        CardVagaComponent,
        AlertModule
    ]
})
export class SharedModule {

}