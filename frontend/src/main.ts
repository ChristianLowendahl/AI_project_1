import { bootstrapApplication } from '@angular/platform-browser';
import { provideHttpClient } from '@angular/common/http';
import { provideAnimations } from '@angular/platform-browser/animations';
import { EmailCheckerComponent } from './app/email-checker/email-checker.component';
import {SummarizeComponent} from './app/summarize/summarize.component';

bootstrapApplication(SummarizeComponent, {
  providers: [
    provideHttpClient(),
    provideAnimations()
  ]
}).catch(err => console.error(err));
