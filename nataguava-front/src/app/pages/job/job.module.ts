import { CommonModule } from '@angular/common';
import { NgModule } from "@angular/core";
import { FormJobComponent } from "./form-job/form-job.component";
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { DetailJobComponent } from './detail-job/detail-job.component';
import { RouterModule } from '@angular/router';
import { NgbTypeaheadModule } from '@ng-bootstrap/ng-bootstrap';
import { ShowIfCandidateModule } from 'src/app/directives/show-if-candidate/show-if-candidate.module';
import { AngularFontAwesomeModule } from 'angular-font-awesome';
import { JobRankingComponent } from './job-ranking/job-ranking.component';

@NgModule({
    declarations: [
        FormJobComponent,
        DetailJobComponent,
        JobRankingComponent
    ],imports: [
        CommonModule,
        FormsModule,
        ReactiveFormsModule,
        RouterModule,
        NgbTypeaheadModule,
        ShowIfCandidateModule,
        AngularFontAwesomeModule
    ],
    exports: [
        FormJobComponent,
        DetailJobComponent
    ]
})
export class JobModule {

}