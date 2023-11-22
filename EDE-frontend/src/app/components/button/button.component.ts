import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-button',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './button.component.html',
  styleUrls: ['./button.component.scss'],
})
export class ButtonComponent {
  @Input() color = 'dark';
  @Input() disabled = false;

  getStyle(): string {
    const options: any = {
      dark: 'bg-gray-800 hover:bg-gray-700 active:bg-gray-900 outline-gray-900',
      warning:
        'bg-orange-800 hover:bg-orange-700 active:bg-orange-900 outline-orange-900',
      success:
        'bg-emerald-800 hover:bg-emerald-700 active:bg-emerald-900 outline-emerald-900',
      danger: 'bg-red-800 hover:bg-red-700 active:bg-red-900 outline-red-900',
      info: 'bg-sky-800 hover:bg-sky-700 active:bg-sky-900 outline-sky-900',
    };
    let style = options[this.color] || options['dark'];
    if (this.disabled) {
      style += ' cursor-not-allowed';
    }
    return style;
  }
}
