import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { JhipsterSharedModule } from '../../shared';
import {
    JobMySuffixService,
    JobMySuffixPopupService,
    JobMySuffixComponent,
    JobMySuffixDetailComponent,
    JobMySuffixDialogComponent,
    JobMySuffixPopupComponent,
    JobMySuffixDeletePopupComponent,
    JobMySuffixDeleteDialogComponent,
    jobRoute,
    jobPopupRoute,
    JobMySuffixResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...jobRoute,
    ...jobPopupRoute,
];

@NgModule({
    imports: [
        JhipsterSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        JobMySuffixComponent,
        JobMySuffixDetailComponent,
        JobMySuffixDialogComponent,
        JobMySuffixDeleteDialogComponent,
        JobMySuffixPopupComponent,
        JobMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        JobMySuffixComponent,
        JobMySuffixDialogComponent,
        JobMySuffixPopupComponent,
        JobMySuffixDeleteDialogComponent,
        JobMySuffixDeletePopupComponent,
    ],
    providers: [
        JobMySuffixService,
        JobMySuffixPopupService,
        JobMySuffixResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class JhipsterJobMySuffixModule {}
