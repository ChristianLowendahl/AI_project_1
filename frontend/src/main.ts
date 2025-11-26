import { bootstrapApplication } from '@angular/platform-browser';
import { provideHttpClient } from '@angular/common/http';
import { provideAnimations } from '@angular/platform-browser/animations';
import { EmailCheckerComponent } from './app/email-checker/email-checker.component';
import { AITextComponent } from './app/aitext/ai-text.component';

bootstrapApplication(AITextComponent, {
  providers: [
    provideHttpClient(),
    provideAnimations()
  ]
}).catch(err => console.error(err));
